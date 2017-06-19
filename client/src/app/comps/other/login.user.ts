import { Component } from '@angular/core';
import {Router} from '@angular/router'
import {UserInfo} from './user-info'

@Component({
  selector: 'login-user',
  templateUrl: '../../../tmpls/other/login.html'
})
export class Login { 
trust = 'Thanga Jothi Gnana Sabai';
loading:boolean;
User : UserInfo = new UserInfo();


 // DI at constructor, private router is member variable 
constructor( private router:Router){
}

doLogin() {
        this.loading = false; 
        if(this.User.LoginName == 'BALU' && this.User.Password == '1234'){
              this.router.navigate(['/ranbarList'])
        }
        else{
           this.router.navigate(['/ranbarAdd'])
            console.log('Auth Error')
        }
        
  } 
}
