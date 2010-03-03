WORK_DIR=screenshots
java -cp target/classes cz.tisnik.ImageDiff.Main -s1=${WORK_DIR}/screenshots-3.3.3.BETA1 -s2=${WORK_DIR}/screenshots-3.3.3.CR1 -masks=${WORK_DIR}/masks -diffs=${WORK_DIR}/diffs -htmlout=${WORK_DIR}/diffs -structdiffs=${WORK_DIR}/diffs
