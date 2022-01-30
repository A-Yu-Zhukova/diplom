const fs = require('fs');
const express = require('express');
const uuidv4 = require('uuid/v4');

const data = JSON.parse(fs.readFileSync('data.json', 'UTF-8'));
console.log(data);

const server = express();
server.use(express.json());

server.post(['/payment', '/credit'], (req, res) => {
  console.log(`Incoming request: ${req.path} ${JSON.stringify(req.body)}`);
  const {body: {number}} = req;
  console.log(number);
  const [item] = data.filter(o => o.number === number);
  console.log(item);
  if (item === undefined) {
    res.status(400).end();
    return;
  }
  console.log(item.status);
  res.send({
    id: uuidv4(),
    status: item.status,
  });
});
console.log(process.env.PORT || 9999);
// get port from environment variable or just use 9999
server.listen(process.env.PORT || 9999);
