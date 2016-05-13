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

//   return [version,sha1]
   return version

}

def  mvnBuild(mvnpath,version) {
   
   sh "${mvnpath} clean versions:set -DnewVersion=${version}"
  // sh "mvn clean versions:set -DnewVersion=${version}-NIGHTLY-${BUILD_TIMESTAMP} 

}


def copy_files_to_node_workspace(node_name,folder)  {
     
     def basedir = new File(folder)


     files = basedir.listFiles()
     fileNames= []
     filePaths = []
    

     for (currentFile in files) { 
          filePaths.push(currentFile.path)
          fileNames.push(currentFile.name)
     }
     
                     
     for (filePath in filePaths) { 
          File f = new File(filePath)
          def fileContent = f.getText()
          def fileName=f.name
          f=null

     /*     def slurper = new JsonSlurper()
          slurped = slurper.parseText( jsonText )
          slurped.service.artifact_url="sdfskkkkddddd"
          def builder = new JsonBuilder(slurped) 

          def content = builder.toPrettyString() */

          currentFile=null
   
          node(node_name) {
               writeFile file: fileName, text: fileContent
          }
     }
}

def deploy_service() {

   sh "sudo chef-client -z -j /home/ec2-user/avreg/deploy_avreg.json -r 'recipe[ec2::deployService]' --log_level info"

   sh "cat /home/ec2-user/avreg.log.ipaddr" 

}

