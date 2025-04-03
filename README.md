# FIB-MCP-SERVER
A lightweight Model-Context-Protocol (MCP) server for interacting with `First Iraqi Bank's Online Payment` API.

## Requirements
1. [Java 21](https://www.oracle.com/java/technologies/javase/jdk21-downloads.html) installed
2. A compatible client, such as [Claude Desktop App](https://claude.ai/download)

## Installation (Claude.ai)
1. Set the required environment variables.
2. Download the JAR file, or build it on your system.
3. Update `claude_desktop_config.json` in **Claude Desktop App**:
    - Navigate to **File → Settings → Developer → Edit Config**.
    - Modify the configuration:
      ```json
      {
        "mcpServers": {
          "fib-pay-mcp-server": {
            "command": "FULL_PATH_TO_JAVA_EXECUTABLE/java.exe",
            "args": [
              "-jar",
              "FULL_PATH_TO_JAR/fib-pay-mcp-0.0.1.jar"
            ],
            "env": {
              "FIB_URL": "YOUR_FIB_URL",
              "FIB_CLIENT_SECRET": "YOUR_CLIENT_SECRET",
              "FIB_CLIENT_ID": "YOUR_CLIENT_ID"
            }
          }
        }
      }
      ```
    - If environment variables are already set in your system, you can omit the `env` block.

4. Alternatively, run the JAR manually:
   ```sh
   java -jar -DFIB_URL=your-url -DFIB_CLIENT_ID=your-client-id -DFIB_CLIENT_SECRET=your-secret fib-pay-mcp-0.0.1.jar

## NOTE
You can use any client, it's not exclusive to `Claude.ai`