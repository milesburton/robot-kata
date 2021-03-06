import command.{BucketFillCommand, DrawLineCommand, DrawRectangleCommand}
import helper.TestRendererUtil
import model.Canvas
import org.scalatest.{BeforeAndAfterEach, FlatSpec, Matchers}

class CanvasSpec extends FlatSpec with Matchers with BeforeAndAfterEach {

  it should "setup an initial grid" in {

    val canvas = new Canvas(10,10)

    val renderedCanvas = TestRendererUtil.render(canvas)

    val expectedCanvas = cleanCanvasExpectation(
      """----------
        |----------
        |----------
        |----------
        |----------
        |----------
        |----------
        |----------
        |----------
        |----------""")

    renderedCanvas should be(expectedCanvas)
  }

  it should "draw a horizontal line" in {

    val canvas = new Canvas(10,10)

    canvas.drawLine(DrawLineCommand(1,2,6,2))

    val renderedCanvas = TestRendererUtil.render(canvas)

    val expectedCanvas = cleanCanvasExpectation(
      """----------
        |xxxxxx----
        |----------
        |----------
        |----------
        |----------
        |----------
        |----------
        |----------
        |----------""")

    renderedCanvas should be(expectedCanvas)
  }

  it should "draw a vertical line" in {

    val canvas = new Canvas(10,10)

    canvas.drawLine(DrawLineCommand(6,3,6,4))

    val renderedCanvas = TestRendererUtil.render(canvas)

    val expectedCanvas = cleanCanvasExpectation(
      """----------
        |----------
        |-----x----
        |-----x----
        |----------
        |----------
        |----------
        |----------
        |----------
        |----------""")

    renderedCanvas should be(expectedCanvas)
  }

  it should "draw a rectangle" in {

    val canvas = new Canvas(10,10)

    canvas.drawRectangle(DrawRectangleCommand(2,1,5,3))

    val renderedCanvas = TestRendererUtil.render(canvas)

    val expectedCanvas = cleanCanvasExpectation(
      """-xxxx-----
        |-x--x-----
        |-xxxx-----
        |----------
        |----------
        |----------
        |----------
        |----------
        |----------
        |----------""")

    renderedCanvas should be(expectedCanvas)
  }

  it should "bucket fill an empty canvas" in {

    val canvas = new Canvas(10,10)

    canvas.bucketFill(BucketFillCommand(3,3,'z'))

    val renderedCanvas = TestRendererUtil.render(canvas)

    val expectedCanvas = cleanCanvasExpectation(
      """zzzzzzzzzz
        |zzzzzzzzzz
        |zzzzzzzzzz
        |zzzzzzzzzz
        |zzzzzzzzzz
        |zzzzzzzzzz
        |zzzzzzzzzz
        |zzzzzzzzzz
        |zzzzzzzzzz
        |zzzzzzzzzz""")

    renderedCanvas should be(expectedCanvas)
  }


  it should "bucket fill around a rectangle" in {

    val canvas = new Canvas(10,10)

    canvas.drawRectangle(DrawRectangleCommand(2,1,5,3))
    canvas.bucketFill(BucketFillCommand(9,9,'z'))

    val renderedCanvas = TestRendererUtil.render(canvas)

    val expectedCanvas = cleanCanvasExpectation(
      """zxxxxzzzzz
        |zx--xzzzzz
        |zxxxxzzzzz
        |zzzzzzzzzz
        |zzzzzzzzzz
        |zzzzzzzzzz
        |zzzzzzzzzz
        |zzzzzzzzzz
        |zzzzzzzzzz
        |zzzzzzzzzz""")

    renderedCanvas should be(expectedCanvas)
  }

  // Resolve the windows/unix line ending mismatch
  def cleanCanvasExpectation(expectation: String) = expectation.stripMargin.replace("\r\n", "\n")
}
