package module2;

import java.util.ArrayList;
import java.util.List;

public class TopsisRankingEngine {

    public static class CandidateDoctor {
        String name;
        double experience; // Criterion 1 (Higher is better)
        double weeklyHours; // Criterion 2 (Lower is better)
        double rating;     // Criterion 3 (Higher is better)
        double topsisScore;

        public CandidateDoctor(String name, double experience, double weeklyHours, double rating) {
            this.name = name;
            this.experience = experience;
            this.weeklyHours = weeklyHours;
            this.rating = rating;
        }
    }

    public static List<CandidateDoctor> rankDoctors(List<CandidateDoctor> doctors) {
        if (doctors.isEmpty()) return doctors;

        int n = doctors.size();
        // Weights for each criterion (Must sum to 1.0)
        double[] weights = {0.4, 0.3, 0.3}; 
        // Benefit criteria (true = higher is better, false = lower is better)
        boolean[] isBenefit = {true, false, true}; 

        // 1. Calculate vector normalization denominators
        double sumExp = 0, sumHours = 0, sumRating = 0;
        for (CandidateDoctor d : doctors) {
            sumExp += d.experience * d.experience;
            sumHours += d.weeklyHours * d.weeklyHours;
            sumRating += d.rating * d.rating;
        }
        double normExp = Math.sqrt(sumExp);
        double normHours = Math.sqrt(sumHours);
        double normRating = Math.sqrt(sumRating);

        // 2. Calculate Weighted Normalized Matrix and find Ideal Best/Worst
        double[][] v = new double[n][3];
        double[] idealBest = {Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY};
        double[] idealWorst = {Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY};

        for (int i = 0; i < n; i++) {
            CandidateDoctor d = doctors.get(i);
            v[i][0] = (normExp == 0 ? 0 : d.experience / normExp) * weights[0];
            v[i][1] = (normHours == 0 ? 0 : d.weeklyHours / normHours) * weights[1];
            v[i][2] = (normRating == 0 ? 0 : d.rating / normRating) * weights[2];

            // Update Ideal Best/Worst based on criteria type
            for (int j = 0; j < 3; j++) {
                if (isBenefit[j]) {
                    if (v[i][j] > idealBest[j]) idealBest[j] = v[i][j];
                    if (v[i][j] < idealWorst[j]) idealWorst[j] = v[i][j];
                } else { // Cost criteria (lower hours is better)
                    if (v[i][j] < idealBest[j]) idealBest[j] = v[i][j];
                    if (v[i][j] > idealWorst[j]) idealWorst[j] = v[i][j];
                }
            }
        }

        // 3. Calculate Separation Measures and Closeness Coefficient
        for (int i = 0; i < n; i++) {
            double sepBest = 0, sepWorst = 0;
            for (int j = 0; j < 3; j++) {
                sepBest += Math.pow(v[i][j] - idealBest[j], 2);
                sepWorst += Math.pow(v[i][j] - idealWorst[j], 2);
            }
            sepBest = Math.sqrt(sepBest);
            sepWorst = Math.sqrt(sepWorst);

            // TOPSIS Performance Score (Closeness to ideal solution)
            doctors.get(i).topsisScore = (sepBest + sepWorst == 0) ? 0 : sepWorst / (sepBest + sepWorst);
        }

        // 4. Sort candidates by score descending
        doctors.sort((d1, d2) -> Double.compare(d2.topsisScore, d1.topsisScore));
        return doctors;
    }
}