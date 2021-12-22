import scala.util.matching.Regex
val isCyrillic = "['\\p{IsCyrillic}]*".r

isCyrillic.matches("Hello")