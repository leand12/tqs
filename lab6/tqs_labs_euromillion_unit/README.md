### 1e/ Has your project passed the defined quality gate? Elaborate your answer.

When looking at the overview tab on SonarQube dashboard, there are many measurements taken, from which we can 

- Bug
  - Something wrong in the code. It may not present problems yet, but it will at the worst moment. It must be fixed immediately.

- Vulnerability
  - A security-related issue which represents a backdoor for attackers.
 
- Security Hotspot
  - Security-sensitive pieces of code. It is not a guaranteed threat, so it needs to be inspected manually.
   
- Code smell (major)
  - A maintainability-related issue in the code. It is a mean of providing good and consistent patterns when writing code, so that when changing the code, it is less likely to introduce new errors.
  
In the initial state of this project, it was presented:
  - 1 bug issue, which is enough to result in a poor reliability (with the actual quality gate);
  - 0 vulnerabilities;
  - 1 security hotspot;
  - 25 code smells, which don't offer a technical debt ratio higher than 5%;
  - 0 duplicated block.

### 1.f/ Explore the analysis results and complete with a few sample issues, as applicable.

| Issue                 | Problem description | How to solve |
|---|---|---|
| Bug                   | Instantiating a new Random object is inefficient and produces predictable results, as the next seed a Random object uses is not randomized. | Save and reuse the Random object.  |
| Code smell (major)    | A for loop stop condition should test the loop counter against an invariant value, as it is less efficient and more difficult to understand otherwise. | Set the stop condition to a local variable just before the loop begins. |
| Code smell (major)    | Standard outputs are not recorded not secure. Besides, their format is not uniform, meaning they are more difficult to read. | Define and use a dedicated logger. |
| Security Hotspot (medium) | The Random class relies on a pseudorandom number generator, so it should not be used for security-critical applications or for protecting sensitive data. | Substitute for the SecureRandom class which relies on a cryptographically strong random number generator (RNG). |

### 2a/ Take note of the technical debt found. Explain what this value means. 

The technical debt is the effort to fix all Code Smells. When the measure is shown in days, it is considered an 8-hour effort per day. In total, it would take 2h09m to fix all code smells from the euromillion project, having the class DemoMain the greatest technical debt, 1h10min.

### 2d/ Run the static analysis and observe/explore the coverage values on the SonarQube dashboard. How many lines are “uncovered”? And how many conditions?

The coverage percentage after the fixes to the code smells decreased from 70.2 to 53.8, and the uncovered conditions increased from 11 to 13, as the new code to fix the major code smells had lines that were partially covered by the tests.