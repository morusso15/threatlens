# ThreatLens

> Analyze tokens, detect phishing, and translate security findings into plain English.

ThreatLens is a full-stack cybersecurity web application that helps users analyze and understand common security artifacts such as JWTs, URLs, and vulnerability findings.

The goal of the project is not just detection, but **explainability** — every result includes reasoning so users can understand *why* something is risky, not just that it is.

---

## 🌐 Live Demo

- **Frontend (Vercel):** https://threatlens-self.vercel.app  
- **Backend API (Render):** https://threatlens-0q7f.onrender.com  

---

## ⭐ Highlights

- Full-stack application using Angular and Spring Boot (Kotlin)
- Deployed across Vercel (frontend) and Render (backend)
- Focus on **explainable security analysis**, not just raw outputs
- Custom heuristics for phishing detection and JWT validation
- Designed to be useful for both technical and non-technical users

---

## 🚀 Features

### 🔐 JWT Inspector

Analyze JSON Web Tokens and identify potential issues.

- Decodes header and payload
- Surfaces important claims:
  - `alg`, `exp`, `iat`, `iss`, `aud`
- Detects insecure configurations, such as `alg: none`
- Optional signature verification:
  - HS256
  - HS384
  - HS512

Useful for debugging authentication issues and identifying weak token configurations.

---

### 🌐 URL Phishing Analyzer

Evaluates URLs for common phishing and malicious patterns using heuristic checks.

Detection includes:

- HTTPS usage
- IP-based domains
- Suspicious keywords, such as login, verify, and secure
- Subdomain depth
- Risky TLDs, such as `.xyz`, `.top`, `.zip`, and `.ru`
- Brand impersonation patterns
- Hyphen-heavy domains

Returns a risk score with detailed explanations for each flag.

---

### 🧠 Findings Translator

Converts raw security findings into clear, human-readable explanations.

- Translates technical vulnerability language into actionable insights
- Helps non-security stakeholders understand risk and impact
- Useful for reports, handoffs, and documentation

---

## 🏗️ Architecture

```text
Angular Frontend (Vercel)
        ↓ REST API
Spring Boot Backend (Render)
        ↓
Security Analysis Logic
```

- Frontend communicates with the backend via REST APIs
- Environment-based configuration is used for API URLs:
  - Local: `http://localhost:8080`
  - Production: Render deployment

---

## 🛠️ Tech Stack

### Frontend

- Angular
- TypeScript
- RxJS
- Vercel

### Backend

- Kotlin
- Spring Boot
- REST APIs
- Render

---

## 📁 Project Structure

```text
threatlens/
├── frontend/
│   └── threatlens-ui/
│       ├── src/app/
│       │   ├── components/
│       │   ├── services/
│       │   └── ...
│       └── environments/
│
├── backend/
│   └── threatlens-api/
│       ├── jwt/
│       ├── url/
│       ├── findings/
│       └── ...
```

---

## ⚙️ Running Locally

### Backend

```bash
cd backend/threatlens-api
./gradlew bootRun
```

The backend runs on:

```text
http://localhost:8080
```

### Frontend

```bash
cd frontend/threatlens-ui
npm install
ng serve
```

The frontend runs on:

```text
http://localhost:4200
```

---

## 🔧 Environment Configuration

The frontend uses Angular environment files:

- `environment.ts` → local development
- `environment.prod.ts` → production

Example:

```ts
export const environment = {
  apiUrl: 'http://localhost:8080'
};
```

---

## 🔮 Future Improvements

- Expand JWT support to include RSA/RS256 verification
- Integrate external threat intelligence sources
- Support exporting or sharing analysis results
- Improve UI for visualization and workflows

---

## 👤 Author

**Mo Russolillo**  
GitHub: https://github.com/morusso15
