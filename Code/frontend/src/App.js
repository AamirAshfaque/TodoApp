
import React, { useState } from 'react';
function App() {
  const [loggedIn, setLoggedIn] = useState(false);
  const [todos, setTodos] = useState([]);
  const [text, setText] = useState('');
  const login = (u, p) => u==='user' && p==='pass';
  return (
    <div>
      {!loggedIn ? (
        <div data-testid="login-form">
          <input data-testid="username" placeholder="user" />
          <input data-testid="password" type="password" placeholder="pass" />
          <button data-testid="login-button"
             onClick={() => {
               const u=document.querySelector('[data-testid=username]').value;
               const p=document.querySelector('[data-testid=password]').value;
               if(login(u,p)) setLoggedIn(true);
               else alert('Invalid');
             }}>Login</button>
        </div>
      ) : (
        <div data-testid="todo-app">
          <input data-testid="new-todo" value={text}
            onChange={e => setText(e.target.value)} />
          <button data-testid="add-button"
            onClick={() => { if(text){ setTodos([...todos, {id:Date.now(), text}]); setText(''); } }}>
            Add</button>
          <ul data-testid="todo-list">
            {todos.map(t => (
              <li key={t.id} data-id={t.id}>
                <span>{t.text}</span>
                <button className="edit">Edit</button>
                <button className="del">Del</button>
              </li>
            ))}
          </ul>
        </div>
      )}
    </div>
  );
}
export default App;
