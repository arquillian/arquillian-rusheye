IMPL=rusheye-impl
TARGET=rusheye-impl/target

if [ ! -d "$TARGET/classes" ]; then
   mvn -f $IMPL/pom.xml compile
fi

if [ ! -d "$TARGET/dependency" ]; then
   mvn -f $IMPL/pom.xml dependency:copy-dependencies
fi

java -cp $TARGET/classes:$TARGET/dependency/* org/jboss/rusheye/Main $*
