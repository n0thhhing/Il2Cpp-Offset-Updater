# IL2CPP AArch64 Offset Updater

## Overview

The Offset Updater is a tool designed to update
offsets for IL2CPP binaries between updates on the AArch64 architecture.
It utilizes Capstone for disassembly and provides a
user-friendly way to manage offsets for dynamic library
updates. The application is built in Kotlin and requires
Java 21 to run.

## Features

- Reads offset configurations from a JSON file.
- Supports pattern scanning in AArch64 binaries using the KMP algorithm.
- Configurable verbosity for detailed output during processing.
- Easy integration with existing projects via Gradle.

## Requirements

- **Java 21**: Ensure you have JDK 21 installed on your system.
- **Kotlin**(only for building): The project is built using Kotlin, ensure you have the necessary dependencies set up in your build configuration.

## Installation

1. Clone the repository or download the project files.
2. Navigate to the project directory.
3. Open the terminal and run the following command to build the project:

   ```bash
   ./gradlew build # or ./gradlew jar to package
   ```

## Configuration

Before running the updater, create a `config.json` file in the project root directory. Here’s an example of the configuration:

```json
{
  "sigLen": 64,
  "verbose": false,
  "offsetFile": "offsets.txt",
  "outputFile": "out.txt",
  "newLib": "new.so",
  "oldLib": "old.so"
}
```

- `sigLen`: Length of the signature for pattern matching.
- `verbose`: Enables detailed logging if set to true.
- `offsetFile`: The file containing the offsets to be updated.
- `outputFile`: Where the new offsets should go
- `newLib`: The new library file to scan.
- `oldLib`: The old library file for reference.

## Offset File Format
Offset File Format
The offsets.txt file must be formatted as follows:
```
Copy code
0xBEAF // comment
0xF00D
0x1234
// comment
```
Each line can contain a hexadecimal offset prefixed with 0x.
Comments can be added using // and will be ignored by the parser.
Empty lines or lines with only comments will be skipped.

## Usage

1. Prepare the `offsets.txt` file containing the offsets to be updated, following the specified format.

2. You can download the release JAR file from the [releases page](https://github.com/n0thhhing/Il2Cpp-Offset-Updater/releases/) and run the updater with the following command:

   ```bash
   java -jar Updater-1.0.0.jar
   ```

   Alternatively, if you built the project yourself, run:

   ```bash
   java -jar build/libs/Updater-1.0.0.jar
   ```

3. The application will load the configuration, disassemble the old library, and search for patterns in the new library.

## Output

The application provides output regarding the status of the offset updates, like whether patterns were found a d the pattern itself. Verbose output can be enabled for detailed information.

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for more information.

## Contributing

Contributions are welcome! Please open issues for any bugs you find or feature requests you may have.

## Acknowledgements

- [Capstone](https://www.capstone-engine.org/) - A lightweight multi-platform, multi-architecture disassembly framework.
- [Gson](https://github.com/google/gson) - A Java library to convert Java Objects into JSON and back.
- [Kotlin](https://kotlinlang.org/) - A modern programming language that makes programming easier and more enjoyable.
- [ktfmt-gradle](https://github.com/cortinico/ktfmt-gradle) A reliable kotlin formatter for gradle.
