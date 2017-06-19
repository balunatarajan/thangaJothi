

import {Injectable} from '@angular/core';
import {Count} from '../../comps/other/Count';
//import {TranInfo} from '../../comps/Book/tran-info';
import {TranCode} from '../../comps/trancode/tran-code';

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

    private getTransactionUrl: string = this.url+ '/tjgs/tran/all';
    private getBookByPageUrl: string = this.url+ '/tjgs/tran/allbypage';
    private postAddBookUrl: string = this.url+ '/tjgs/tran/add';
    private putAddBookUrl: string = this.url+ '/tjgs/tran/modify';
    private getCountUrl: string = this.url+'/tjgs/getCount'; 

    private postAddTranCodeUrl: string = this.url+ '/tjgs/tranhead/add';
    private putTranCodeUrl: string = this.url+ '/tjgs/tranhead/modify';
    private getTranCodeUrl: string = this.url+ '/tjgs/tranhead/all';

constructor(private http : Http){}

public  getTransCountInDb() :Observable<Count> {
        //getCount?table=anbar
        console.log('BookService::getTransCountInDb Enter');
        return this.http.get(this.getCountUrl+'?table=trans')
                  .map(this.extractCount);
}

extractCount(resp: Response){
        console.log('BookService::extractCount Enter');
        let body = resp.json();
        return body;
}

public addNewBook(ti:TranInfo){
      
            console.log('BookService::addNewBook Enter');
            var headers = new Headers ({ 'Content-Type': 'application/json','Accept':'application/json' });
            let options = new RequestOptions({ headers: headers });
            var tranToPost = JSON.stringify(ti);
            
            console.log('Post Tran : before service call : '+tranToPost);
            return  this.http.post(this.postAddBookUrl, tranToPost, options).
                                                map((res:any) => res.json());
}

public getBookByPage(pageSize : number,pageNo : number) :Observable<TranInfo[]> {
        console.log('BookService::getBookByPage Enter');
        console.log('URL ' + this.getBookByPageUrl+'?page='+pageNo+'&size='+pageSize);
        return this.http.get(this.getBookByPageUrl+'?page='+pageNo+'&size='+pageSize)
                    .map(this.extractTrans);
}

//utility method
extractTrans(resp: Response){        
        console.log('BookService::getBookByPage Enter');
        let body = resp.json();
        console.log('Book DATA>'+JSON.stringify(body));
        return body || [] ;  // if body is null return empty array []
}

public modifyBook(ti:TranInfo) {
           console.log('BookService::modifyBook Enter');
           // headers.append('Access-Control-Allow-Origin','*');

            var headers = new Headers ({ 'Content-Type': 'application/json','Accept':'application/json' });
            let options = new RequestOptions({ headers: headers });
            var tranToPost = JSON.stringify(ti);
            
            return  this.http.put(this.putAddBookUrl, tranToPost, options).
                                                map((res:any) => {
                                                
                                                res.json()
                                                });                                                
 }

///////// TRAN CODE ///////////////////////////////

public addNewTranCode(ti:TranCode){
           console.log('BookService::addNewTranCode Enter');
      
            var headers = new Headers ({ 'Content-Type': 'application/json','Accept':'application/json' });
            let options = new RequestOptions({ headers: headers });
            var tranToPost = JSON.stringify(ti);
            
            console.log('Post Tran : before service call : '+tranToPost);
            return  this.http.post(this.postAddTranCodeUrl, tranToPost, options).
                                                map((res:any) => res.json());
}


public modifyTranCode(ti:TranCode){
            console.log('BookService::modifyTranCode Enter');
      
             // headers.append('Access-Control-Allow-Origin','*');

            var headers = new Headers ({ 'Content-Type': 'application/json','Accept':'application/json' });
            let options = new RequestOptions({ headers: headers });
            var tranToPost = JSON.stringify(ti);
            
            console.log('Post transcation : before service call : '+tranToPost);
            return  this.http.put(this.putTranCodeUrl, tranToPost, options).
                                                map((res:any) => res.json());                                                
 }


public getAllTranCode() :Observable<TranCode[]> {
        
        console.log('BookService::getAllTranCode Enter');
      
        return this.http.get(this.getTranCodeUrl)
                    .map(this.extractTranCode);
}

//utility method
extractTranCode(resp: Response){        
        console.log('BookService::extractTranCode Enter');
      
        let body = resp.json();
        console.log('TRAN Code>'+JSON.stringify(body));
        return body || [] ;  // if body is null return empty array []
}

public  getTransCodeInDb() :Observable<Count> {
        console.log('BookService::getTransCodeInDb Enter');
        console.log('Inside getAnbarCountInDb .. '+this.getCountUrl+'?table=tcode');
        return this.http.get(this.getCountUrl+'?table=tcode')
                  .map(this.extractTCCount);
}

extractTCCount(resp: Response){
        let body = resp.json();
        return body;
}

}