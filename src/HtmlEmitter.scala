import java.io.{File, PrintWriter}
import scala.util.Random

object HtmlEmitter extends App {
  val r = new Random()
  case class Robot(name: String, strength: Int)
  val robots = 1 to 10 map(n => Robot(s"Robot $n", r.nextInt(101)))
  val p = new PrintWriter(new File("/tmp/robots.html"))

  p.println("<!DOCTYPE html>")
  p.println(/*
    <html>
      <head></head>
      <body>
        <h1>Robot Information</h1>
        <table>
          <thead>
            <tr>
              <td>Name</td>
              <td>Strength</td>
            </tr>
          </thead>
          <tbody>
            {robots.map(r =>
            <tr>
              <td>{r.name}</td>
              <td>{r.strength}</td>
            </tr>
            )}
          </tbody>
        </table>
      </body>
    </html>
  */)
  p.close()
}
