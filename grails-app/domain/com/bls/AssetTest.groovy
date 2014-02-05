package com.bls

@Mixin(DynamicPropertyMixin)
class AssetTest {
  String keywords
  String comment
  String helpText
  Long dateCreated = new Date().getTime()   // Default Grail's Event
  Long dateUpdated = new Date().getTime()
  Long dateDeleted = null
  String createdByUserApId = null
  String updatedByUserApId = null
  String deletedByUserApId = null
  Boolean canBeModified = true

  List<String> conceptApIds = []

  static hasMany = [conceptApIds: String]

  static mapping = {
    version false
    tablePerHierarchy false
    table 'astTEST'
    comment column: 'comment_str'
  }

  static constraints = {
    canBeModified(default: true)
    helpText(maxSize: 1000, nullable: true, blank: true)
    keywords(maxSize: 200, nullable: true, blank: true)
    comment(maxSize: 200, nullable: true, blank: true)
    dateDeleted(nullable: true, blank: true)
    dateUpdated(nullable: true, blank: true)
    createdByUserApId(nullable: false, blank: false, maxSize: 36)
    updatedByUserApId(nullable: true, blank: false, maxSize: 36, default: null)
    deletedByUserApId(nullable: true, blank: false, maxSize: 36, default: null)
  }


  void replaceConceptApIds(List<String> apIdList) {
    if( conceptApIds == null ) {
      conceptApIds =  []
    } else {
      conceptApIds.clear()
    }
    if( apIdList ) {
      apIdList.each {apId->
        if (apId)conceptApIds<< apId
      }
    } else {
      // then apIdList are either empty or it is null
      // either way assume we are to clear the conceptApIds
      conceptApIds.clear()
    }
  }


  void addAllToConcepts(List<String> concepts) {
    println "IN addAllToConcepts"
  }

  void removeFromConcepts(String concept) {
    println "IN removeFromConcepts"
  }

  void addConceptApId(String conceptApId) {
    println "IN addConceptApId"
  }

  void addToConcepts(String concept) {
    println "IN addToConcepts"
  }

  List<String> getConcepts() {
    println "IN getConcepts"
  }
}
