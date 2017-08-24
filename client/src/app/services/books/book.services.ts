

import {Injectable} from '@angular/core';
import {Count} from '../../comps/other/Count';
//import {TranInfo} from '../../comps/Book/tran-info';
import {BookTran} from '../../comps/books/booktran';
import {MasterDataC} from '../../comps/other/masterData';
import {BookCount} from '../../comps/books/book-count';
import {BookTransfer} from '../../comps/books/book-transfer';

import {Http,Response,RequestOptions,RequestMethod,Headers} from '@angular/http';
import {Observable} from 'rxjs/Observable';
        import 'rxjs/add/operator/map';
//import 'rxjx/add/operator/catch';

@Injectable()
export class BookService {

    getData:string;
    private serverIp:string ='blrlw4258';
    private serverPort:string='4040';
    private url : string = 'http://'+this.serverIp+':'+this.serverPort;

    private getTAllBookTranUrl: string = this.url+ '/rest/tjgs/booktran/all';
    private getTBookTranGrouByUrl: string = this.url+ '/rest/tjgs/booktran/groupby';
    private getbyowner: string = this.url+ '/rest/tjgs/booktran/owner';
    private getbytitle: string = this.url+ '/rest/tjgs/booktran/title';
    private getbyOwnerTitle: string = this.url+ '/rest/tjgs/booktran/';
    
    private postAddBookUrl: string = this.url+ '/rest/tjgs/booktran/add';
    private putAddBookUrl: string = this.url+ '/rest/tjgs/booktran/modify';
    private getCountUrl: string = this.url+'/rest/tjgs/getCount'; 
    private getBookUrl: string = this.url+ '/rest/tjgs/book/all';
    private sellBookUrl: string = this.url+ '/rest/tjgs/booktran/sell';
    private transferBookUrl: string = this.url+ '/rest/tjgs/booktran/transfer';
    private getBookOwnersUrl: string = this.url+'/rest/tjgs/booktran/bookowners'; 
                                                           
constructor(private http : Http){}

public  getBookOwners() :Observable<string[]> {
        //getCount?table=anbar
        console.log('BookService::getBookOwners Enter');
        return this.http.get(this.getBookOwnersUrl)
                  .map(this.extractJson);
}

public  getBookTranByOwner(name:string) :Observable<BookTran[]> {
        //getCount?table=anbar
        console.log('BookService::getBookTranByOwner Enter');
        return this.http.get(this.getbyowner+'/'+name)
                  .map(this.extractJson);
}

public  getBookTranByTitle(title:string) :Observable<BookTran[]> {
        //getCount?table=anbar
        console.log('BookService::getBookTranByTitle Enter');
        return this.http.get(this.getbytitle+'/'+title)
                  .map(this.extractJson);
}

public  getBookTranByOwnerTitle(owner:string,title:string) :Observable<BookTran[]> {
        //getCount?table=anbar
        console.log('BookService::getBookTranByOwnerTitle Enter');
        return this.http.get(this.getbyOwnerTitle+'/'+title+'/'+owner)
                  .map(this.extractJson);
}


public  getBookTranAll() :Observable<BookTran[]> {
        //getCount?table=anbar
        console.log('BookService::getBookTranAll Enter');
        return this.http.get(this.getTAllBookTranUrl)
                  .map(this.extractJson);
}

public  getBookTranGroupBy() :Observable<BookCount[]> {
        //getCount?table=anbar
        console.log('BookService::getBookTranGroupBy Enter');
        return this.http.get(this.getTBookTranGrouByUrl)
                  .map(this.extractJson);
}

public  getAllBookNames() :Observable<MasterDataC[]> {
        //getCount?table=anbar
        console.log('BookService::getAllBookNames Enter');
        return this.http.get(this.getBookUrl)
                  .map(this.extractJson);
}

public  getBookTransCountInDb() :Observable<Count> {
        //getCount?table=anbar
        console.log('BookService::getTransCountInDb Enter');
        return this.http.get(this.getCountUrl+'?table=trans')
                  .map(this.extractJson);
}

public addNewBook(bt:BookTran[] ){
      
            console.log('BookService::addNewBook Enter');
            var headers = new Headers ({ 'Content-Type': 'application/json','Accept':'application/json' });
            let options = new RequestOptions({ headers: headers });
            var tranToPost = JSON.stringify(bt);
            
            console.log('Post Tran : before service call : '+tranToPost);
            return  this.http.post(this.postAddBookUrl, tranToPost, options).
                                                map((res:any) => res.json());
}
public sellBook(bt:BookTran[] ){
      
            console.log('BookService::sellBook Enter');
            var headers = new Headers ({ 'Content-Type': 'application/json','Accept':'application/json' });
            let options = new RequestOptions({ headers: headers });
            var tranToPost = JSON.stringify(bt);
            
            console.log('Post Tran : before service call : '+tranToPost);
            return  this.http.post(this.sellBookUrl, tranToPost, options).
                                                map((res:any) => res.json());
}

public transferBook(bt:BookTransfer[] ){
      
            console.log('BookService::sellBook Enter');
            var headers = new Headers ({ 'Content-Type': 'application/json','Accept':'application/json' });
            let options = new RequestOptions({ headers: headers });
            var tranToPost = JSON.stringify(bt);
            
            console.log('Post Tran : before service call : '+tranToPost);
            return  this.http.post(this.transferBookUrl, tranToPost, options).
                                                map((res:any) => res.json());
}
/*
public getBookByPage(pageSize : number,pageNo : number) :Observable<BookTran[]> {
        console.log('BookService::getBookByPage Enter');
        console.log('URL ' + this.getBookByPageUrl+'?page='+pageNo+'&size='+pageSize);
        return this.http.get(this.getBookByPageUrl+'?page='+pageNo+'&size='+pageSize)
                    .map(this.extractJson);
}*/

public modifyBook(ti:BookTran) {
           console.log('BookService::modifyBook Enter');
           // headers.append('Access-Control-Allow-Origin','*');

            var headers = new Headers ({ 'Content-Type': 'application/json','Accept':'application/json' });
            let options = new RequestOptions({ headers: headers });
            var tranToPost = JSON.stringify(ti);
            
            return  this.http.put(this.putAddBookUrl, tranToPost, options).
                                                map((res:any) => {res.json()});                                                
}

extractJson(resp: Response){
        let body = resp.json();
        return body;
}
}