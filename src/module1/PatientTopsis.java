package module1;

import java.util.*;

public class PatientTopsis {

    // ── Inner classes (same pattern as lecturer's template) ───────────────────

    /** Holds patient name + criteria scores */
    static class PatientData {
        String patientID;
        String name;
        double[] criteria; // [age, recordCount, daysSinceVisit]

        PatientData(String patientID, String name, double[] criteria) {
            this.patientID = patientID;
            this.name      = name;
            this.criteria  = criteria;
        }
    }

    /** Holds final ranking result */
    static class RankResult {
        String patientID;
        String name;
        double score;
        int    rank;

        RankResult(String patientID, String name, double score) {
            this.patientID = patientID;
            this.name      = name;
            this.score     = score;
        }
    }

    // ── TOPSIS Core (identical algorithm to lecturer's template) ──────────────

    /**
     * Runs TOPSIS on a decision matrix.
     * @param matrix    m×n matrix (m patients, n criteria)
     * @param weights   weight for each criterion (must sum to 1.0)
     * @param isBenefit true = higher is better, false = lower is better
     * @return closeness scores for each patient (higher = higher priority)
     */
    public static double[] topsis(double[][] matrix, double[] weights, boolean[] isBenefit) {

        int m = matrix.length;        // number of patients
        int n = matrix[0].length;     // number of criteria

        double[][] normalized = new double[m][n];
        double[][] weighted   = new double[m][n];

        // Step 1 — Normalize the matrix (vector normalization)
        for (int j = 0; j < n; j++) {
            double sumSquares = 0.0;
            for (int i = 0; i < m; i++) {
                sumSquares += Math.pow(matrix[i][j], 2);
            }
            double denom = Math.sqrt(sumSquares);
            for (int i = 0; i < m; i++) {
                normalized[i][j] = (denom == 0) ? 0 : matrix[i][j] / denom;
            }
        }

        // Step 2 — Apply weights
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                weighted[i][j] = normalized[i][j] * weights[j];
            }
        }

        // Step 3 — Determine ideal best and ideal worst
        double[] idealBest  = new double[n];
        double[] idealWorst = new double[n];

        for (int j = 0; j < n; j++) {
            idealBest[j]  = weighted[0][j];
            idealWorst[j] = weighted[0][j];
            for (int i = 1; i < m; i++) {
                if (isBenefit[j]) {
                    idealBest[j]  = Math.max(idealBest[j],  weighted[i][j]);
                    idealWorst[j] = Math.min(idealWorst[j], weighted[i][j]);
                } else {
                    idealBest[j]  = Math.min(idealBest[j],  weighted[i][j]);
                    idealWorst[j] = Math.max(idealWorst[j], weighted[i][j]);
                }
            }
        }

        // Step 4 — Calculate Euclidean distances to ideal best and worst
        double[] distBest  = new double[m];
        double[] distWorst = new double[m];

        for (int i = 0; i < m; i++) {
            double sumBest = 0.0, sumWorst = 0.0;
            for (int j = 0; j < n; j++) {
                sumBest  += Math.pow(weighted[i][j] - idealBest[j],  2);
                sumWorst += Math.pow(weighted[i][j] - idealWorst[j], 2);
            }
            distBest[i]  = Math.sqrt(sumBest);
            distWorst[i] = Math.sqrt(sumWorst);
        }

        // Step 5 — Calculate closeness score
        double[] scores = new double[m];
        for (int i = 0; i < m; i++) {
            double denom = distBest[i] + distWorst[i];
            scores[i] = (denom == 0) ? 0 : distWorst[i] / denom;
        }

        return scores;
    }

    // ── Public API ────────────────────────────────────────────────────────────

    /**
     * Ranks a list of patients by treatment priority using TOPSIS.
     *
     * Criteria weights:
     *   Age               — 40% (most important)
     *   Medical records   — 35%
     *   Days since visit  — 25%
     *
     * All criteria are benefit type (higher value = higher priority).
     *
     * @param patients list of PatientData objects
     * @return sorted list of RankResult (rank 1 = highest priority)
     */
    public static List<RankResult> rankPatients(List<PatientData> patients) {

        if (patients == null || patients.isEmpty())
            throw new IllegalArgumentException("Patient list cannot be empty.");

        int m = patients.size();
        int n = 3; // criteria count

        // Build decision matrix
        double[][] matrix = new double[m][n];
        for (int i = 0; i < m; i++) {
            matrix[i] = patients.get(i).criteria;
        }

        // Define weights (must sum to 1.0)
        double[] weights   = { 0.40, 0.35, 0.25 };

        // All three criteria are benefit (higher = better)
        boolean[] isBenefit = { true, true, true };

        // Run TOPSIS
        double[] scores = topsis(matrix, weights, isBenefit);

        // Build result list
        List<RankResult> results = new ArrayList<>();
        for (int i = 0; i < m; i++) {
            results.add(new RankResult(
                    patients.get(i).patientID,
                    patients.get(i).name,
                    scores[i]));
        }

        // Sort descending (highest score = highest priority = rank 1)
        results.sort((a, b) -> Double.compare(b.score, a.score));

        // Assign rank numbers
        for (int i = 0; i < results.size(); i++) {
            results.get(i).rank = i + 1;
        }

        return results;
    }

    /**
     * Convenience method — prints the ranking to console.
     * Same output style as lecturer's template.
     */
    public static void printRanking(List<RankResult> results) {
        System.out.println("============================================");
        System.out.println("  TOPSIS Patient Priority Ranking");
        System.out.println("  Criteria: Age | Medical Records | Days Since Visit");
        System.out.println("  Weights : 40% | 35%            | 25%");
        System.out.println("============================================");
        for (RankResult r : results) {
            System.out.printf("  Rank %d: %-20s  Score = %.4f%n",
                    r.rank, r.name + " (" + r.patientID + ")", r.score);
        }
        System.out.println("============================================");
        System.out.println("  Best Alternative (Highest Priority): "
                + results.get(0).name);
        System.out.println("============================================");
    }

    // ── Demo main (matches format of lecturer's template) ─────────────────────
    public static void main(String[] args) {

        // Sample patients — in real use these come from PatientDatabase
        // Format: PatientData(patientID, name, new double[]{age, recordCount, daysSinceVisit})
        List<PatientData> patients = new ArrayList<>();
        patients.add(new PatientData("PT1001", "Ahmad Bin Ali",       new double[]{65, 8, 30}));
        patients.add(new PatientData("PT1002", "Siti Binti Hassan",   new double[]{45, 3, 10}));
        patients.add(new PatientData("PT1003", "Raj Kumar",           new double[]{72, 12, 60}));
        patients.add(new PatientData("PT1004", "Lim Mei Ling",        new double[]{30, 1, 5}));
        patients.add(new PatientData("PT1005", "Nurul Ain Binti Zain",new double[]{58, 6, 45}));

        List<RankResult> results = rankPatients(patients);
        printRanking(results);
    }
}