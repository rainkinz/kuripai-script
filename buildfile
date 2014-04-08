# Good example file here: http://svn.apache.org/repos/asf/ode/trunk/Rakefile

# require File.expand_path(File.dirname(__FILE__) + "/../shared_packages.rb")

desc 'Scripts used by the framework'
define 'kuripai-script' do
  THIS_VERSION='1.0'
  project.group = 'org.kuripai'
  project.version = THIS_VERSION

    repositories.release_to[:username] = "admin"
    repositories.release_to[:password] = "m0t01"
    repositories.release_to[:url] = "http://cruise:8081/nexus/content/repositories/releases/"  

  manifest["Copyright"] = "Brendan Grainger (C)"

  compile.options.target = "1.6"
  compile.options.source = "1.6"

  # compile.options.lint = "all"
  # compile.with JAVAX.servlet,  SPRING

  #compile.with 'aopalliance:aopalliance:jar:1.0', 'avalon-framework:avalon-framework:jar:4.1.3', 'commons-fileupload:commons-fileupload:jar:1.1.1', 'commons-io:commons-io:jar:1.1', 'commons-logging:commons-logging:jar:1.0.4', 'freemarker:freemarker:jar:2.3.8', 'javax.servlet:jsp-api:jar:2.0', 'javax.servlet:servlet-api:jar:2.3', 'log4j:log4j:jar:1.2.12', 'logkit:logkit:jar:1.0.1', 'ognl:ognl:jar:2.6.9', 'opensymphony:sitemesh:jar:2.2.1', 'opensymphony:xwork:jar:2.0.0', 'org.apache.struts:struts2-api:jar:2.0.5', 'org.apache.struts:struts2-core:jar:2.0.5', 'org.apache.struts:struts2-sitemesh-plugin:jar:2.0.5', 'org.apache.struts:struts2-spring-plugin:jar:2.0.5', 'org.springframework:spring-beans:jar:2.0.1', 'org.springframework:spring-context:jar:2.0.1', 'org.springframework:spring-core:jar:2.0.1', 'org.springframework:spring-web:jar:2.0.1', 'uk.ltd.getahead:dwr:jar:1.1-beta-3'

  package :jar, :id => 'kuripai-script'

  javadoc projects
  package :javadoc
end
