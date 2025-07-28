Test Plan: React‑Todo App

1. What is being tested:
   - React frontend: login flow + CRUD of todo items
   - Backend API: /login, /items endpoints (CRUD + auth)

2. Coverage:
   - Valid/invalid login in UI
   - Create, edit, delete todo via UI
   - GET, POST, PUT, DELETE items via API (positive/negative cases)

3. Tools:
   - Selenium WebDriver with Node.js and ChromeDriver — for UI automation (handles React DOM + waits)
   - Supertest + Jest — for API tests, simulating HTTP calls directly

4. Execution:
   - Start backend server: `cd backend && npm install && npm start`
   - Start frontend server: `cd frontend && npm install && npm start`
   - UI tests: `jest --config=../frontend/jest.config.js ui.test.js`
   - API tests: `cd backend && npm test`

5. Assumptions & limitations:
   - In-memory state resets on each run
   - No real authentication token validation
   - UI edit simulated (no dedicated input field)
   - Tests run locally on Chrome; headless/sauce/cloud not included

