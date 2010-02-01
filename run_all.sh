WORK_DIR=test_data_
java -cp bin cz.tisnik.ImageDiff.Main -s1=${WORK_DIR}/source1 -s2=${WORK_DIR}/source2 -masks=${WORK_DIR}/masks -diffs=${WORK_DIR}/diffs -htmlout=${WORK_DIR}/html
