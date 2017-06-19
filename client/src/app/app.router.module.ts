//Routing confgiuration module which will have app wide
// valid URLS/routes the app will serve 
// export the confgiuration so that the root module 
// can import and initiate 

import {ModuleWithProviders} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';

//app imports
import {AnbarList} from './comps/anbar/anbar.list';
import {Login} from './comps/other/login.user';
import {SignIn} from './comps/other/signin';
import {ContactUs} from './comps/other/contactus';
import {TransactionList} from './comps/transaction/transaction.list';
import {TranCodeList} from './comps/trancode/trancode.list';
import {BookList} from './comps/books/books.list';

// array of valid URLs of this path
const appRoutePaths=[
    {path:'rlogIn', component:Login},
    {path:'rsignIn', component:SignIn},
    {path:'rcontactUs', component:ContactUs},
    {path:'ranbarList', component:AnbarList},
    {path:'rTranList', component:TransactionList},
    {path:'rTranCode', component:TranCodeList},
    {path:'rBookList', component:BookList},
    
    {path:'', redirectTo:'/rcontactUs',pathMatch:'full'}
];

// export all the routes with the router related ModuleWithProviders
export const appRoutesModule: ModuleWithProviders =RouterModule.forRoot(appRoutePaths);



