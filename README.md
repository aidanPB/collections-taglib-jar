# jsp-collections-taglib
## JSP Custom Tags for working with the Collections Framework and indexed/mapped properties.
### Global Attributes:
* Optional:
  * `id`: document-unique id for the tag.
  * `valueVar`: the scripting-variable name to which each value of a collection or property will be bound. Default `id` + "Value", or "cValue" if no id was specified.

### Currently Implemented:
Nothing.

### Planned:
* _forEachInList_: Evaluates its body for each member of an indexed collection.
  * Required attributes:
    * `collection`: the `java.util.List` over which the tag will iterate. This should be a JSP expression or an EL value expression.
  * Optional attributes:
    * `from`: the first index for which to evaluate the tag's body. Default `0`.
    * `to`: the last index for which to evaluate the tag's body. Default `collection.size() - 1`.
    * `step`: the quantity by which the index should be incremented for each iteration. Default `1`.
    * `indexVar`: the scripting-variable name to which the current index will be bound. Default `id` + "Index", or "cIndex" if no id was specified.
  * Scripting variables:
    * `cIndex`: the current index into the collection. Nested scope.
    * `cValue`: the value in the collection at the current index. Nested scope.
* _forEachIndexedProperty_: Evaluates its body for each value of an indexed property on a Java Bean.
  * Required attributes: 
    * `name`: the name of the bean variable, as declared in a prior scriptlet or `<jsp:useBean id="name">`.
    * `property`: the name of the indexed property to access.
  * Optional attributes:
    * `from`: the first index for which to evaluate the tag's body. Default `0`.
    * `to`: the last index for which to evaluate the tag's body. Default `n`, where `n` is the last index of the property.
    * `step`: the quantity by which the index should be incremented for each iteration. Default `1`.
    * `indexVar`: the scripting-variable name to which the current index will be bound. Default `id` + "Index", or "cIndex" if no id was specified.
  * Scripting variables:
    * `cIndex`: the current index into the property. Nested scope.
    * `cValue`: the value of the property at the current index. Nested scope.
* _forEachInMap_: Evaluates its body for each key-value pair in a Map.
  * Required attributes:
    * `collection`: the `java.util.Map` over which the tag will iterate. This should be a JSP expression or an EL value expression.
  * Optional attributes:
    * `keyVar`: the scripting-variable name to which the current key will be bound. Default `id` + "Key", or "cKey" if no id was specified.
  * Scripting variables:
    * `cKey`: the current key on the map. Nested scope.
    * `cValue`: the value in the map for the current key. Nested scope.
* _forEachMappedProperty_: Evaluates its body for each key-value pair of a mapped property on a Java Bean. Mapped properties are not strictly part of the Java Beans Framework, but they're an extremely common extension.
  * Required attributes: 
    * `name`: the name of the bean variable, as declared in a prior scriptlet or `<jsp:useBean id="name">`.
    * `property`: the name of the mapped property to access.
  * Optional attributes:
    * `keyVar`: the scripting-variable name to which the current key will be bound. Default `id` + "Key", or "cKey" if no id was specified.
  * Scripting variables:
    * `cKey`: the current key on the property. Nested scope.
    * `cValue`: the value of the property for the current key. Nested scope.
