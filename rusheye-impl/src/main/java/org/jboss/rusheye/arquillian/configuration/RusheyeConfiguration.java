package org.jboss.rusheye.arquillian.configuration;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;
import org.apache.commons.lang.StringUtils;

/**
 *
 * @author jhuska
 */
public class RusheyeConfiguration extends Configuration<RusheyeConfiguration> {

    private String suiteDescriptor = "suite.xml";

    private String workingDirectory = "target";

    private String maskBase = "masks";

    private String onePixelTreshold = null;

    private String globalDifferenceTreshold = null;

    private String globalDifferenceAmount = null;

    private String suiteListener = null;

    private String force = "false";

    private String resultOutputFile = "result.xml";

    private String diffsDir = "diffs";

    public boolean isForce() {
        return Boolean.parseBoolean(getProperty("force", force));
    }

    public String getSuiteListener() {
        return getProperty("suiteListener", suiteListener);
    }

    public String getResultOutputFile() {
        return getProperty("resultOutputFile", resultOutputFile);
    }

    public String getGlobalDifferenceAmount() {
        return getProperty("globalDifferenceAmount", globalDifferenceAmount);
    }

    public Integer getGlobalDifferenceTreshold() {
        String result = getProperty("globalDifferenceTreshold", globalDifferenceTreshold);
        return result != null ? Integer.parseInt(result) : null;
    }

    public Integer getOnePixelTreshold() {
        String result = getProperty("onePixelTreshold", onePixelTreshold);
        return result != null ? Integer.parseInt(result) : null;
    }

    public File getWorkingDirectory() {
        return new File(getProperty("workingDirectory", workingDirectory));
    }

    public File getMaskBase() {
        return new File(getProperty("maskBase", maskBase));
    }

    public File getDiffsDir() {
        return new File(getProperty("diffsDir", diffsDir));
    }

    public File getSuiteDescriptor() {
        return new File(getProperty("suiteDescriptor", suiteDescriptor));
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%-40s %s\n", "resultOutputFile", getSuiteDescriptor()));
        return sb.toString();
    }

    @Override
    public void validate() throws RusheyeConfigurationException {
        List<String> messages = constructMessages();
        messages.add(validateOutputFile("Suite descriptor", getSuiteDescriptor()));
        messages.add(validateOnePixelTreshold());
        messages.add(validateGlobalDifferenceTreshold());
        messages.add(validateGlobalDifferenceAmount());

        if (!messages.isEmpty()) {
            throw new RusheyeConfigurationException(StringUtils.join(messages, '\n'));
        }
    }

    private String validateOnePixelTreshold() {
        if (getOnePixelTreshold() != null && (getOnePixelTreshold() < 0 || getOnePixelTreshold() > 768)) {
            return "One pixel treshold must be integer in range 0-768";
        }
        return null;
    }

    private String validateGlobalDifferenceTreshold() {
        if (getGlobalDifferenceTreshold() != null && (getGlobalDifferenceTreshold() < 0 || getGlobalDifferenceTreshold() > 768)) {
            return "Global difference treshold must be integer in range 0-768";
        }
        return null;
    }

    private String validateGlobalDifferenceAmount() {
        if (globalDifferenceAmount != null) {
            boolean matches = false;
            Pattern[] pixelAmountPatterns = new Pattern[]{Pattern.compile("\\d+px"), Pattern.compile("([0-9]{1,2}|100)%")};
            for (Pattern pattern : pixelAmountPatterns) {
                if (pattern.matcher(globalDifferenceAmount).matches()) {
                    matches = true;
                    break;
                }
            }
            if (!matches) {
                return "Global difference pixel must be amount of pixels perceptually different - % of image surface or integer of pixels differ";
            }
        }
        return null;
    }

    private String validateOutputFile(String name, File file) {
        if (file != null) {
            if (file.exists() && !isForce()) {
                return name + " file '" + file.getPath() + "' already exists (use --force/-f to overwrite)";
            }
            if (file.exists() && !file.canWrite()) {
                return name + " file '" + file.getPath() + "' can't be written";
            }
        }
        return null;
    }

    private String validateInputDirectory(String name, File directory) {
        if (directory != null) {
            if (!directory.exists()) {
                return name + " directory '" + directory.getPath() + "' doesnt exist)";
            }

            if (!directory.isDirectory()) {
                return name + " directory '" + directory.getPath() + "' isn't directory)";
            }
        }
        return null;
    }

    @SuppressWarnings("serial")
    private List<String> constructMessages() {
        return new LinkedList<String>() {
            public boolean add(String e) {
                if (e == null) {
                    return false;
                }
                return super.add(e);
            }
        ;
    }
;
}
}
