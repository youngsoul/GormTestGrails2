package com.bls

class Car {

  String name
  Engine engine  // note in the 'hasOne' relationship the Engine must be saved prior to Car
                  // and the engine will not be deleted when the car is deleted.
                  // Car will cascade saves to Engine


  static constraints = {
    engine(nullable: true)
  }
//  static mapping = {
//    tablePerHierarchy(false)
//    //engine fetch: 'join' --to always load an engine when a car is loaded
//  }

  // Note that this has to be afterDelete and not beforeDelete
  def afterDelete() {
      println "Before delete of a Car: Engine Discontinued: ${engine.discontinued}"
      if( engine.discontinued ) {
        println "Deleting engine with id: ${engine.id}"
        //engine.delete()  <-- NOTE this wont work here, you have to executeUpdate
        Engine.executeUpdate("delete from Engine e where e.id = :eid", [eid: engine.id])
      }

  }

}
