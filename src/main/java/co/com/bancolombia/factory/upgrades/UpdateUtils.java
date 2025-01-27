package co.com.bancolombia.factory.upgrades;

import co.com.bancolombia.factory.ModuleBuilder;
import java.io.IOException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UpdateUtils {

  public static boolean appendIfNotContains(
      ModuleBuilder builder, String file, String contains, String toAdd) throws IOException {
    return builder.updateFile(
        file,
        content -> {
          if (!content.contains(contains)) {
            return content + toAdd;
          }
          return content;
        });
  }

  public static boolean updateVersions(
      ModuleBuilder builder, String file, String property, String version) throws IOException {
    boolean appliedA =
        builder.updateExpression(file, "(" + property + "\\s?=\\s?)'.+'", "$1'" + version + "'");
    boolean appliedB =
        builder.updateExpression(file, "(" + property + "\\s?=\\s?)\".+\"", "$1'" + version + "'");
    return appliedA || appliedB;
  }

  public static boolean updateConfiguration(
      ModuleBuilder builder, String file, String configuration, String newConfiguration)
      throws IOException {
    boolean appliedA =
        builder.updateExpression(file, "(" + configuration + "\\s)", newConfiguration + " ");
    boolean appliedB =
        builder.updateExpression(file, "(" + configuration + "\\()", newConfiguration + "(");
    return appliedA || appliedB;
  }

  public static String appendValidate(
      String main, String match, String containsValue, String concatValue) {
    if (main.contains(containsValue)) {
      return main;
    }
    int start = main.indexOf(match);
    return main.substring(0, start) + concatValue + main.substring(start);
  }

  public static String replace(String content, String previous, String next) {
    return content.replace(previous, next);
  }
}
