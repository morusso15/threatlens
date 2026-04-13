# ThreatLens

## Overview
ThreatLens is a lightweight cybersecurity toolkit web application designed to demonstrate practical security concepts through interactive tools. The application combines multiple security-focused utilities into a single, clean dashboard experience.

This project is built to showcase full-stack development skills, including frontend design, backend API development, and applied cybersecurity knowledge.

---

## Core Concept
ThreatLens is not a single-purpose app. It is a **multi-tool cybersecurity dashboard** that allows users to:

- Analyze authentication tokens
- Detect potentially malicious URLs
- Translate technical security findings for different audiences

---

## Features

### 1. JWT Inspector
Allows users to paste a JSON Web Token (JWT) and analyze it.

**Capabilities:**
- Decode header and payload
- Display algorithm used
- Show expiration and issued time
- Highlight potential risks
- Provide human-readable explanations

**Purpose:**
Demonstrates understanding of authentication systems and token-based security.

---

### 2. URL Phishing Analyzer
Analyzes URLs for common phishing and malicious patterns.

**Capabilities:**
- Detect suspicious domains and subdomains
- Identify misleading brand usage
- Check for HTTP vs HTTPS
- Detect IP-based URLs
- Evaluate URL structure and keywords
- Generate a risk score

**Purpose:**
Shows practical cybersecurity analysis and threat detection logic.

---

### 3. Security Finding Translator (Planned)
Transforms technical vulnerability descriptions into different audience formats.

**Target Outputs:**
- Executive summary
- Technical remediation guidance
- Customer-friendly explanation
- Compliance-style language

**Purpose:**
Demonstrates ability to communicate security risks effectively across audiences.

---

## Tech Stack

### Frontend
- Angular
- TypeScript
- SCSS

**Responsibilities:**
- UI/UX
- Routing between tools
- Input forms
- Displaying analysis results

---

### Backend
- Kotlin
- Spring Boot

**Responsibilities:**
- API endpoints
- Token parsing and analysis
- URL analysis logic
- Data validation

---

### Deployment
- Frontend: Vercel
- Backend: (Render / Railway / Fly.io)

---

## Architecture

```
threatlens/
  frontend/
    threatlens-ui/   # Angular app
  backend/
    threatlens-api/  # Kotlin Spring Boot API
```

---

## API Endpoints

### Health Check
```
GET /api/health
```

### JWT Analysis
```
POST /api/jwt/analyze
```

Request:
```
{
  "token": "<JWT_TOKEN>"
}
```

---

### URL Analysis
```
POST /api/url/analyze
```

Request:
```
{
  "url": "https://example.com"
}
```

---

## Development Roadmap

### Phase 1: Foundation
- Set up Angular frontend
- Set up Kotlin backend
- Create app layout and routing
- Implement dashboard UI

### Phase 2: Core Features
- Build JWT Inspector
- Build URL Phishing Analyzer
- Connect frontend to backend APIs

### Phase 3: Expansion
- Implement Security Finding Translator
- Add UI polish and improved UX
- Add risk scoring and explanations

### Phase 4: Deployment
- Deploy frontend to Vercel
- Deploy backend API
- Connect production endpoints

---

## Goals

This project aims to demonstrate:
- Full-stack development capability
- Understanding of cybersecurity fundamentals
- Ability to design useful tools
- Clean UI/UX thinking
- Clear communication of technical concepts

---

## Project Pitch

ThreatLens is a cybersecurity toolkit that enables users to analyze authentication tokens, detect suspicious URLs, and translate security findings for different audiences through a unified web interface.

---

## Future Improvements
- Add authentication for users
- Store analysis history
- Integrate external threat intelligence APIs
- Enhance translator with AI/LLM support
- Improve scoring models

---

## Author

Built by Mo Russolillo as a technical demonstration project.

