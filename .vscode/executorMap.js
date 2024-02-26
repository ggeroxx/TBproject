const fs = require('fs');

const isWindows = process.platform === 'win32';

const executorMap = {
        "java": isWindows
            ? "cd $dir && cd ../.vscode && node executorMap.js && cd $dir && javac -cp .;..\\lib\\mysql-connector-j-8.3.0.jar -d ..\\bin\\ $fileName && java -cp .;..\\lib\\mysql-connector-j-8.3.0.jar;..\\bin Main"
            : "cd $dir && cd ../.vscode && node executorMap.js && cd $dir && javac -cp .:../lib/mysql-connector-j-8.3.0.jar -d ../bin/ $fileName && java -cp .:../lib/mysql-connector-j-8.3.0.jar:../bin Main"
};

const additionalProperties = {
    "java.project.sourcePaths": ["src"],
    "java.project.outputPath": "bin",
    "java.project.referencedLibraries": [
        "lib/**/*.jar"
    ]
};

const finalConfig = {
    ...{ "code-runner.executorMap": executorMap },
    ...additionalProperties
};

const jsonData = JSON.stringify(finalConfig, null, 2);

fs.writeFileSync('settings.json', jsonData, 'utf-8');
