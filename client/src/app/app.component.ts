import { Component } from '@angular/core';
//import {UserInfo} from './comps/other/user-info';
import {Router} from '@angular/router';
//import { FormsModule } from '@angular/forms';
@Component({
  selector: 'my-app',
   templateUrl: './tmpls/other/app.Comp.html'
})
export class AppComponent { 
trust = 'Thanga Jothi Gnana Sabai';
userLoggedIn : number;
//loading:boolean;
//User : UserInfo = new UserInfo();


 // DI at constructor, private router is member variable 
 constructor( private router:Router){
    this.userLoggedIn = 0;
 }

/*doLogin() {
        this.loading = false; 
        if(this.User.LoginName == 'BALU' && this.User.Password == '1234'){
              this.router.navigate(['/uAdd'])
        }
        else{
            console.log('Auth Error')
        }*/
        /*this.authService.Authenticate(this.User.LoginName,this.User.Password).then(
            status => {
                this.failed = !status;
                if(status){
                    this.router.navigate(['/catalog'])
                }
            }
            ,err => console.log('Auth Error'+err)
        )*/
    //}
}
