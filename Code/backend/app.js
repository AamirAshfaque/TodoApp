const express = require('express');
const bodyParser = require('body-parser');
const app = express();
app.use(bodyParser.json());
let items = [];
const USERS = {user: 'pass'};
app.post('/login', (req, res) => {
  const {username, password} = req.body;
  if(USERS[username] === password) res.json({token: 'fake-token'});
  else res.status(401).json({error: 'Unauthorized'});
});
app.get('/items', (req, res) => res.json(items));
app.post('/items', (req, res) => {
  const item = {...req.body, id: items.length+1};
  items.push(item);
  res.status(201).json(item);
});
app.put('/items/:id', (req, res) => {
  const id = +req.params.id;
  const i = items.find(x=>x.id===id);
  if(!i) return res.status(404).json({error: 'Not found'});
  Object.assign(i, req.body);
  res.json(i);
});
app.delete('/items/:id', (req, res) => {
  const id = +req.params.id;
  const idx = items.findIndex(x=>x.id===id);
  if(idx<0) return res.status(404).json({error: 'Not found'});
  items.splice(idx,1);
  res.status(204).end();
});
if(require.main===module) app.listen(5000, ()=>console.log('API up'));
module.exports = app;
