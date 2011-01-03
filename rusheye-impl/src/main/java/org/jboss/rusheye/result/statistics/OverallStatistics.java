/**
 * JBoss, Home of Professional Open Source
 * Copyright ${year}, Red Hat, Inc. and individual contributors
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.jboss.rusheye.result.statistics;

import static org.jboss.rusheye.suite.ResultConclusion.ERROR;

import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicLong;

import org.jboss.rusheye.result.ResultStatistics;
import org.jboss.rusheye.suite.Pattern;
import org.jboss.rusheye.suite.Properties;
import org.jboss.rusheye.suite.ResultConclusion;
import org.jboss.rusheye.suite.Test;

/**
 * @author <a href="mailto:lfryc@redhat.com">Lukas Fryc</a>
 * @version $Revision$
 */
public class OverallStatistics implements ResultStatistics {

    PrintWriter printerWriter;

    Map<ResultConclusion, AtomicLong> conclusionStatistics = new LinkedHashMap<ResultConclusion, AtomicLong>();

    {
        for (ResultConclusion conclusion : ResultConclusion.values()) {
            conclusionStatistics.put(conclusion, new AtomicLong(0));
        }
    }

    @Override
    public void setProperties(Properties properties) {
        Writer writer = (Writer) properties.getProperty("overall-statistics-output");

        if (writer == null) {
            writer = new OutputStreamWriter(System.out);
        }

        this.printerWriter = new PrintWriter(writer);
    }

    @Override
    public void onPatternCompleted(Pattern pattern) {
        if (pattern.getConclusion() == ERROR) {
            addConclusion(ERROR);
        }
    }

    @Override
    public void onTestCompleted(Test test) {
        ResultConclusion bestConclusion = ERROR;

        for (Pattern pattern : test.getPatterns()) {
            if (pattern.getConclusion().ordinal() < ERROR.ordinal()) {
                bestConclusion = pattern.getConclusion();
            }
        }

        if (bestConclusion != ERROR) {
            addConclusion(bestConclusion);
        }

        printerWriter.println("[ " + bestConclusion + " ] " + test.getName());
        printerWriter.flush();
    }

    @Override
    public void onSuiteCompleted() {
        printerWriter.println();
        printerWriter.println("=====================");
        printerWriter.println("  Overall Statistics:");

        for (Entry<ResultConclusion, AtomicLong> entry : conclusionStatistics.entrySet()) {
            long count = entry.getValue().get();

            if (count > 0) {
                printerWriter.println("  " + entry.getKey() + ": " + count);
            }
        }

        printerWriter.println("=====================");

        printerWriter.flush();
    }

    private void addConclusion(ResultConclusion conclusion) {
        conclusionStatistics.get(conclusion).incrementAndGet();
    }
}
