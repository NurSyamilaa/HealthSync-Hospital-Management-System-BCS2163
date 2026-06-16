package module2;
public class StaffFactory {
    
    // Factory method returns the abstract base type (Staff) polymorphically
    public static Staff createStaff(String role, String id, String name, String email, String spec, String license) {
        if (role == null) {
            return null;
        }
        
        // Decides what specific child object instance to construct based on role parameter
        if (role.equalsIgnoreCase("DOCTOR")) {
            return new Doctor(id, name, email, spec, license);
        }
        
        // Easily extendable to create Nurse or Admin profiles
        return null;
    }
}