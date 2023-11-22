# Jsonable<br>
<h2>Usage:<h2>
Person person = new Person(20, "Sasha");
Jsonable jsonable = new Jsonable();
String result = jsonable.valueToJsonString(person);
OR
String result = jsonable.valueToJsonString(new Integer[]{1, 2, 3, 4, 123141});
<br>
<br>
valueToJsonString can take anything except arrays of primitive types. Use arrays of wrapper classes instead.
