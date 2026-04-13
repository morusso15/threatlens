# ThreatLens

ThreatLens is a lightweight full-stack cybersecurity web application built to demonstrate practical security analysis through a clean web interface. It combines focused security utilities into a single dashboard, making it useful as both a portfolio project and a foundation for expanding into a broader security toolkit.

The project currently includes:

- `JWT Inspector` for decoding JSON Web Tokens, reviewing claims, and checking common security signals
- `URL Phishing Analyzer` for flagging suspicious URLs, risky patterns, and deceptive structure
- `Finding Translator` as a planned feature for rewriting security findings for different audiences

## Why This Project

ThreatLens was designed to showcase:

- Full-stack application development
- Practical cybersecurity-focused logic
- API design and frontend integration
- Clear presentation of technical findings

## Tech Stack

### Frontend

- Angular 21
- TypeScript
- SCSS
- Bun for package management

### Backend

- Kotlin
- Spring Boot 4
- Gradle
- Java 21

## Project Structure

```text
threatlens/
├── backend/
│   └── threatlens-api/
└── frontend/
    └── threatlens-ui/
```

## Current Features

### JWT Inspector

The JWT Inspector accepts a token and returns a structured analysis of:

- Header and payload contents
- Signing algorithm
- Expiration and issued-at claims
- Issuer and audience claims
- Optional HMAC signature verification when a secret is provided
- Human-readable findings that summarize risk and token quality

### URL Phishing Analyzer

The URL analyzer checks a submitted URL for common phishing indicators, including:

- Missing HTTPS
- IP-based hosts
- Suspicious keywords such as `login`, `verify`, and `password`
- Excessive subdomains
- A simple risk score with supporting findings

### Planned: Finding Translator

The next planned tool is a finding translator that can rewrite security issues for different audiences, such as executives, engineers, customers, or compliance reviewers.

## API Endpoints

The backend currently exposes:

```http
GET /api/health
POST /api/jwt/analyze
POST /api/url/analyze
```

Example JWT request:

```json
{
  "token": "<JWT_TOKEN>",
  "secret": "optional-shared-secret"
}
```

Example URL request:

```json
{
  "url": "https://example.com"
}
```

## Running Locally

### Backend

From `backend/threatlens-api`:

```bash
./gradlew bootRun
```

On Windows:

```powershell
.\gradlew.bat bootRun
```

The API will run on `http://localhost:8080`.

### Frontend

From `frontend/threatlens-ui`:

```bash
bun install
bun run start
```

The frontend will run on `http://localhost:4200`.

## Deployment

### Frontend Deployment

The frontend is a good fit for Vercel or Netlify.

Recommended flow:

1. Build the Angular app from `frontend/threatlens-ui`
2. Publish the production build output
3. Point the frontend to the deployed backend API URL

Typical production build:

```bash
bun run build
```

Important note: the frontend services currently call `http://localhost:8080` directly. Before deploying, move that API base URL into environment configuration or update it to your production backend URL.

### Backend Deployment

The backend is a good fit for Render, Railway, or Fly.io.

Recommended flow:

1. Deploy the Spring Boot app from `backend/threatlens-api`
2. Use Java 21 in the runtime environment
3. Build with Gradle and expose the assigned HTTP port
4. Update CORS settings to allow your deployed frontend origin

Typical production build:

```bash
./gradlew build
```

For local development, CORS currently allows `http://localhost:4200`. In production, update the allowed origin in `backend/threatlens-api/src/main/kotlin/com/threatlens/api/WebConfig.kt`.

## Sharing a Demo with ngrok

If you want to share the app quickly without a full deployment, `ngrok` is a simple option.

### Option 1: Share the frontend only

Run the frontend locally:

```bash
ngrok http 4200
```

This is useful if your backend is already deployed somewhere public.

### Option 2: Share the backend only

Run the backend locally:

```bash
ngrok http 8080
```

This is useful for testing the API remotely with tools like Postman or a deployed frontend.

### Option 3: Share both for a full local demo

Run both services locally and expose each with its own tunnel:

```bash
ngrok http 4200
ngrok http 8080
```

If you do this, make sure:

- The frontend is configured to call the public backend `ngrok` URL
- The backend CORS settings allow the frontend `ngrok` domain

For the cleanest public demo, the most reliable setup is usually:

- Deploy the backend once
- Tunnel only the frontend during development

## Roadmap

- Add the Finding Translator feature
- Improve production configuration for API base URLs
- Expand analysis depth and scoring logic
- Add tests around security analysis behavior
- Improve deployment readiness and environment configuration

## Author

Built by Mo Russolillo as a cybersecurity-focused full-stack demonstration project.
