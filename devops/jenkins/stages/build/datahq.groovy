/**
 * Builds application from source using maven
 * @param   vars        object with all pipeline variables
 * @param   services    applications to work with
 * @see     populateTasks
 */
def build() {
  sh "export SOURCE_HOME=/home/jhopper/DataHQ/workspace/DataHQ_AIX_MN_hourly"
  sh "export LIBPATH=/cots/2.0/RogueWave/SourcePro/Ed11/lib:/cots/2.0/identitysystems/bin:/cots/2.0/Borland/VisiBroker/lib:/cots/2.0/gsoap-2.7/lib:/cots/2.0/tiff-3.8.2/lib:/cots/2.0/cups-1.3.9/lib:/cots/2.0/curl-7.19.4/lib:/cots/2.0/xerces-2.7/lib:/cots/2.0/xalan-1.10/lib:/cots/2.0/pdflib-7.0.4/lib:/u01/app/oracle/product/10.2.0/client_1/lib32:/usr/lib:/usr/mqm/lib:/usr/java6/lib:/opt/subversion/lib:/home/onfnprod/root/lib:/cots/2.0/RogueWave/SourcePro/Ed11/lib:$SOURCE_HOME/lib:$SOURCE_HOME/testlib:$LOG4CPP_LIB:$GTEST_LIB"
  sh "make -j 6"
}
return this;
