import grails.artefact.Enhanced
import org.codehaus.groovy.grails.commons.GrailsApplication

class BootStrap {
  GrailsApplication grailsApplication
    def init = { servletContext ->

      grailsApplication.domainClasses.each { domainClass ->
        def enhanced = domainClass.clazz.getAnnotation(Enhanced) as Enhanced
        enhanced?.mixins().each { mixin ->
          domainClass.clazz.mixin(mixin)
        }
      }
    }
    def destroy = {
    }
}
