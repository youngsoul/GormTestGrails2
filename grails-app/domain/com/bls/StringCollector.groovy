package com.bls

class StringCollector {

  List<String> myStrings = []
  Long dateCreated = new Date().getTime()
  Long dateUpdated = new Date().getTime()

  String name
  static hasMany = [myStrings:String]

    static constraints = {

    }
  static mapping = {
    version false
    tablePerHierarchy( false )
  }


  List<String> getThings() {
    println "In getThings"
  }
}
