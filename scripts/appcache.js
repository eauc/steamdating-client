"use strict";

const fs = require("fs");
const glob = require("glob");
const {staticFileGlobs} = require("../sw_precache_config.json");

const files = staticFileGlobs
      .map((pattern) => glob.sync(pattern))
      .reduce((mem, val) => mem.concat(val), [])
      .filter((path) => fs.lstatSync(path).isFile())
      .map((path) => path.replace(/^resources\/public\//, ""));

const cache = [
  "CACHE MANIFEST",
  `# ${(new Date()).toISOString()}`,
  "",
  "CACHE:",
].concat(files).concat([
  "",
  "NETWORK:",
  "*",
]).join("\n");

const outputFileName = "./resources/public/appcache.manifest";
fs.writeFileSync(outputFileName, cache);
console.log(outputFileName, " =");
console.log(cache);
