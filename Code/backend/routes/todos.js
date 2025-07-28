const express = require('express');
const router = express.Router();

let items = [];
const USERS = { user: 'pass' };

// POST /login
router.post('/login', (req, res) => {
  const { username, password } = req.body;
  if (USERS[username] === password) {
    res.json({ token: 'fake-token' });
  } else {
    res.status(401).json({ error: 'Unauthorized' });
  }
});

// GET /items
router.get('/items', (req, res) => {
  res.json(items);
});

// POST /items
router.post('/items', (req, res) => {
  const newItem = { id: items.length + 1, ...req.body };
  items.push(newItem);
  res.status(201).json(newItem);
});

// PUT /items/:id
router.put('/items/:id', (req, res) => {
  const id = parseInt(req.params.id, 10);
  const item = items.find(i => i.id === id);
  if (!item) return res.status(404).json({ error: 'Not found' });
  Object.assign(item, req.body);
  res.json(item);
});

// DELETE /items/:id
router.delete('/items/:id', (req, res) => {
  const id = parseInt(req.params.id, 10);
  const index = items.findIndex(i => i.id === id);
  if (index === -1) return res.status(404).json({ error: 'Not found' });
  items.splice(index, 1);
  res.status(204).end();
});

module.exports = router;
