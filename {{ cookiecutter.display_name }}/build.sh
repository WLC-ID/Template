MVN_VERSION=$(mvn -q -Dexec.executable=echo -Dexec.args='${project.version}' --non-recursive exec:exec)
ORIGIN_VERSION=$MVN_VERSION
METHOD=$(echo -e ${1} | tr -d '[:space:]')
if [ $METHOD = "dev" ]; then
  MVN_VERSION=$(echo ${MVN_VERSION} |  awk -F'.' '{print $1"."$2"."$3+1}' |  sed s/[.]$//)
elif [ $METHOD = "minor" ]; then
  MVN_VERSION=$(echo ${MVN_VERSION} |  awk -F'.' '{print $1"."$2+1"."0}' |  sed s/[.]$//)
elif [ $METHOD = "major" ]; then
  MVN_VERSION=$(echo ${MVN_VERSION} |  awk -F'.' '{print $1+1"."0"."0}' |  sed s/[.]$//)
elif [ $METHOD = "rebuild" ]; then
  mvn clean install
  echo Rebuilding Version: $MVN_VERSION
  exit 1
else
  echo Method not found.
  exit 1
fi
mvn -U versions:set -DnewVersion=${MVN_VERSION}
mvn clean install
echo "Version: $ORIGIN_VERSION -> $MVN_VERSION"