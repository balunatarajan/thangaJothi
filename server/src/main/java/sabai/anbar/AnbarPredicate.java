package sabai.anbar;

import com.mysema.query.types.expr.BooleanExpression;
import com.mysema.query.types.path.NumberPath;
import com.mysema.query.types.path.PathBuilder;
import com.mysema.query.types.path.StringPath;

public class AnbarPredicate {

/*	 //Custom Predicate with PathBuilder
	 //We’re using PathBuilder here instead of the automatically generated 
	// Q-types because we need to create paths dynamically for more abstract usage:
    	private SearchCriteria criteria;
    
	   public BooleanExpression getPredicate() {
	            PathBuilder<Anbargal> entityPath = new PathBuilder<>(Anbargal.class, "user");
	     
	            if (isNumeric(criteria.getValue().toString())) {
	                NumberPath<Integer> path = entityPath.getNumber(criteria.getKey(), Integer.class);
	                int value = Integer.parseInt(criteria.getValue().toString());
	                switch (criteria.getOperation()) {
	                    case ":":
	                        return path.eq(value);
	                    case ">":
	                        return path.goe(value);
	                    case "<":
	                        return path.loe(value);
	                }
	            } 
	            else {
	                StringPath path = entityPath.getString(criteria.getKey());
	                if (criteria.getOperation().equalsIgnoreCase(":")) {
	                    return path.containsIgnoreCase(criteria.getValue().toString());
	                }
	            }
	            return null;
	        }

	private boolean isNumeric(String string) {
		// TODO Auto-generated method stub
		return false;
	}*/
}	  
