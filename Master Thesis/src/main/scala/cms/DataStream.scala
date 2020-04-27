package cms

import scala.collection.mutable.ArrayBuffer

class DataStream(val size: Int) {

  private val data:ArrayBuffer[Float] =  ArrayBuffer.empty //Math.round(r.nextFloat() * 1000.00) / 1000.00f

  def getData: ArrayBuffer[Float] = data

  def addElement(elem: Float): Unit = {
    if (data.size < size) data.append(elem)
    else {
      data.remove(0)
      data.append(elem)
    }
  }

  def distinct(): Int = {
    data.distinct.size
  }
}
