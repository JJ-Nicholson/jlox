package com.craftinginterpreters.lox;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * The main driver class for the Lox interpreter.
 */
public class Lox {
  /**
   * Entry point for the Lox interpreter.
   *
   * <p>
   * Depending on the number of command-line arguments, the interpreter
   * runs in one of two modes:
   * </p>
   *
   * <ul>
   * <li><b>Script mode</b>: executes a Lox source file.</li>
   * <li><b>REPL mode</b>: starts an interactive prompt.</li>
   * </ul>
   *
   * <p>
   * If more than one argument is provided, the program prints usage
   * information and exits with status code {@code 64}. (The exit code
   * is from the UNIX “sysexits.h” header.)
   * </p>
   *
   * @param args command-line arguments; zero or one argument expected
   * @throws IOException if an I/O error occurs while reading source code
   */
  public static void main(String[] args) throws IOException {
    if (args.length > 1) {
      System.out.println("Usage: jlox [script]");
      System.exit(64);
    } else if (args.length == 1) {
      runFile(args[0]);
    } else {
      runPrompt();
    }
  }

  /**
   * Reads and executes the Lox source file at {@code path}.
   *
   * @param path the filesystem path to the Lox source file
   * @throws IOException if the file cannot be read
   */
  /*
   * Implementation note:
   *
   * Files.readAllBytes(Path) is a facade over Java NIO. At runtime, the Path
   * carries a reference to the JVM's FileSystem, which selects an appropriate
   * FileSystemProvider (e.g., Unix or Windows) within a method called within
   * Files.readAllBytes(Path). The provider invokes JDK native code via JNI
   * to issue OS system calls (open/read/close), returning raw bytes and
   * translating kernel errors into IOExceptions.
   */
  private static void runFile(String path) throws IOException {
    byte[] bytes = Files.readAllBytes(Paths.get(path));
    run(new String(bytes, Charset.defaultCharset()));
  }

  /**
   * Runs the Lox interpreter in interactive prompt (REPL) mode.
   *
   * <p>
   * The method repeatedly reads a line of input from standard input,
   * executes it immediately, and continues until an end-of-file condition
   * (e.g., Control-D if in the terminal on Mac) is encountered.
   * </p>
   *
   * @throws IOException if an I/O error occurs while reading input
   */
  /*
   * Implementation note:
   *
   * Standard input is provided to the JVM at startup via the operating
   * system’s stdin file descriptor. If input is piped or redirected
   * (e.g., java Lox < file.lox or echo "print 1;" | java Lox),
   * the OS assigns that file or pipe input to stdin before the JVM begins.
   *
   * System.in is a JVM-managed InputStream wrapping this descriptor.
   * The input stream is decoded into characters by InputStreamReader
   * and buffered by BufferedReader, enabling efficient line-based
   * input via readLine().
   */
  private static void runPrompt() throws IOException {
    InputStreamReader input = new InputStreamReader(System.in);
    BufferedReader reader = new BufferedReader(input);

    while (true) {
      System.out.print("> ");
      String line = reader.readLine();
      if (line == null)
        break;
      run(line);
    }
  }

  /**
   * Executes a unit of Lox source code.
   *
   * @param source the Lox source text to execute
   */
  /*
   * Implementation note:
   *
   * The interpreter pipeline has not yet been implemented, e.g., the
   * Scanner and Token classes are yet to be written.
   */
  private static void run(String source) {
    // Scanner scanner = new Scanner(source);
    // List<Token> tokens = scanner.scanTokens();

    // for (Token token : tokens) {
    // System.out.println(token);
    // }
  }
}
