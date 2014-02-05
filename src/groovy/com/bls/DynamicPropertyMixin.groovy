package com.bls

/**
 *
 * User: youngsoul
 * Date: 2/4/14
 * Time: 7:47 PM
 *
 */
class DynamicPropertyMixin {

  void addDynamicProperty(DynamicProperty value) {
    value.ownerId = id
    value.save(flush:true, failOnError: true)
  }

  List<DynamicProperty> getDynamicProperties() {
    return DynamicProperty.findAllByOwnerId(id)
  }
}
