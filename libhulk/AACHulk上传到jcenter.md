Terminal执行下面的命令

1.project中的build.gradle配置jcenter
//上传到jcenter的相关配置
classpath 'com.jfrog.bintray.gradle:gradle-bintray-plugin:1.8.0'
classpath 'com.github.dcendents:android-maven-gradle-plugin:2.1'

2.jcenter.gradle
Android Library上传到jcenter的相关配置

3.lib的build.gradle最后一行配置jcenter.gradle
apply from: "jcenter.gradle"

3.Terminal中执行上传到jcenter的命令
./gradlew install
./gradlew bintrayUpload

⚠️bash: ./gradlew: Permission denied错误执行
chmod +x gradlew

