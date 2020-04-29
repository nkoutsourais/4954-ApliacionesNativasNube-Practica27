const { spawnSync } = require('child_process');

function exec(serviceName, command, cwd) {
    console.log(`Installing dependencies for [ ${serviceName} ]`);
    console.log(`Folder: ${cwd} Command: ${command} `);
    spawnSync(command, [], { cwd, shell: true, stdio: 'inherit' });
}
exec('api-gateway', 'gradlew assemble', './api-gateway');
exec('monolito', 'mvn install', './monolito');
exec('orderservice', 'mvn install', './orderservice');