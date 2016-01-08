public class Abstract_Mode_parts{
  public boolean saveData() {
  	database.showData();
  	String className = this.getClass().getSimpleName();
  	if (!stringMap.isEmpty()) {
  		database.setData(className + "_stringMap", stringMap);
  		database.showData();
  		return true;
  	} else if (!longMap.isEmpty()) {
  		database.setData(className + "_longMap", longMap);
  		database.showData();
  		return true;
  	} else if (!integerMap.isEmpty()) {
  		database.setData(className + "_integerMap", integerMap);
  		return true;
  	} else if (!booleanMap.isEmpty()) {
  		database.setData(className + "_booleanMap", booleanMap);
  		return true;
  	} else if (!objectMap.isEmpty()) {
  		database.setData(className + "_objectMap", objectMap);
  		return true;
  	} else if (!doubleMap.isEmpty()) {
  		database.setData(className + "_doubleMap", doubleMap);
  		return true;
  	} else if (!maps.isEmpty()) {
  		database.setData(className + "_maps", maps);
  		return true;
  	} else if (!stack.empty()) {
  		database.setData(className + "_stack", this.stack);
  		database.showData();
  		return true;
  	}

  	return false;
  }
}