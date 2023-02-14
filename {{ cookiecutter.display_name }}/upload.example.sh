USER=root
TARGET_DIR="~/server/plugins"
REMOTE_HOST=0.0.0.0
KEY_DIR="../keyprivate"

VERSION=$(mvn -q -Dexec.executable=echo -Dexec.args='${project.version}' --non-recursive exec:exec)
NAME=$(mvn -q -Dexec.executable=echo -Dexec.args='${project.artifactId}' --non-recursive exec:exec)
NAME=$(echo -e ${NAME} | tr -d '[:space:]')

if [ $NAME == "" ]; then
  echo Project Name is Empty
  exit 1
fi
echo Uploading ${NAME}-${VERSION} to ${REMOTE_HOST}
ssh -i ${KEY_DIR} ${USER}@${REMOTE_HOST} -- "rm ${TARGET_DIR}/${NAME}*.jar"
scp -i ${KEY_DIR} target/${NAME}-${VERSION}.jar ${USER}@${REMOTE_HOST}:${TARGET_DIR}/${NAME}-${VERSION}.jar