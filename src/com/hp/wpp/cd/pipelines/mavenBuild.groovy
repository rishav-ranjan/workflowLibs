ockage com.hp.wpp.cd.pipelines 

def checkOut(credentialsId,url,version_tag) {

   echo "$credentialsId"
   echo "$url"
   echo "$version_tag"

   //git credentialsId: '7a9d9c98-fec1-4a3c-82ff-53295d9d5c9b', url: 'https://github.com/WPPg2/avatar-reg.git'
   git credentialsId: "$credentialsId", url: "$url"

   echo "sdfsd"

   def matcher = readFile('pom.xml') =~ '<avreg.version>(.+)</avreg.version>'
//   def matcher = readFile('pom.xml') =~ "<${version_tag}>(.+)</avreg.version>"
   def version = matcher[0][1] 
   // because matcher is not a serializable variable.
   matcher = null
  // matcher ? matcher[0][1] : null

///   def build_version = this.get_version("$version_tag")

   echo "Building version ${version}"
   echo "build id is ${env.BUILD_ID}"
   
   
   sh "git rev-parse HEAD > sha1"
   
   def sha1 = readFile('sha1')
   echo "sha1 is ${sha1}"

   return [version,sha1]

}

def  mvnBuild(mvnpath,version) {
   
   sh "${mvnpath} clean versions:set -DnewVersion=${version}"
  // sh "mvn clean versions:set -DnewVersion=${version}-NIGHTLY-${BUILD_TIMESTAMP} 

}

//def get_version() {
//  def matcher = readFile('pom.xml') =~ '<avreg.version>(.+)</avreg.version>'
//  matcher ? matcher[0][1] : null
//}

