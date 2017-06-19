import { NgModule }      from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import {Router} from '@angular/router';
import {HttpModule} from '@angular/http';
//import { NgGridModule } from "angular2-grid";
//import {InputTextModule,DataTableModule,ButtonModule,DialogModule} from 'primeng/primeng';
import {MaterialModule, MdNativeDateModule} from '@angular/material';
import {BrowserAnimationsModule,NoopAnimationsModule} from '@angular/platform-browser/animations';
import {MdDatepickerModule} from '@angular/material';

import { appRoutesModule }  from './app.router.module';
import { AppComponent }  from './app.component';

import {AnbarList}  from './comps/anbar/anbar.list';
import {SignIn}  from './comps/other/signin';
import {Login}  from './comps/other/login.user';
import {ContactUs}  from './comps/other/contactus';
import {AnbarService} from './services/anbar/anbar.service';
import {TransactionService} from './services/transaction/transaction.service';
import {TransactionList} from './comps/transaction/transaction.list';
import {TranCodeList} from './comps/trancode/trancode.list';
import {BookList} from './comps/books/books.list';
//import { MaterialModule } from '@angular/material';

@NgModule({
  imports:      [ BrowserModule,BrowserModule,appRoutesModule,HttpModule,FormsModule,
                  MdDatepickerModule,MaterialModule, MdNativeDateModule,NoopAnimationsModule],
  //InputTextModule,DataTableModule,ButtonModule,DialogModule],
  
  declarations: [ AppComponent,SignIn,Login,ContactUs,AnbarList,TransactionList,TranCodeList,BookList],
  bootstrap:    [ AppComponent ],
  providers: [AnbarService,TransactionService]
})
export class AppModule { }
