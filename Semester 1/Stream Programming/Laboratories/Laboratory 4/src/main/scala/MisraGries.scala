import scala.collection.mutable.Map

class MisraGries(val amountOfCounters: Int) {

  def misraGries(stream: Stream[Any]): (Map[Any, Int]) = {
    var counted = Map[Any, Int]()
    for (elem <- stream) {
      if (counted.keySet.contains(elem.getClass)) counted.update(elem.getClass, counted(elem.getClass) + 1)
      else {
        if (counted.keys.size < amountOfCounters) {
          counted.update(elem.getClass, 1)
        }
        else {
          for(key <- counted.keys){
            counted.update(key, counted(key)-1)
            if(counted(key)==0) counted.remove(key)
          }
        }
      }
    }
    counted
  }
}
