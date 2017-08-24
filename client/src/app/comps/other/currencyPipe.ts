import { Pipe, PipeTransform } from '@angular/core';
 
@Pipe({ name: 'rupees' })
export class IndianRsPipe implements PipeTransform {
 
  transform(value: any) {
      if (value) {
        return value.charAt(0).toUpperCase() + value.slice(1);
      }
      return value;
  }
}

//The PipeTransform Interface
//The transform method is essential to a pipe. The PipeTransform interface defines that 
//method and guides both tooling and the compiler. It is technically optional; Angular looks for
// and executes the transform method regardless.


@Pipe({ name: 'capitalize' })
export class CapitalizePipe implements PipeTransform{
  transform(text: string, numLetters: number){
    if(numLetters === undefined){
      return text.toUpperCase();
    } else {
      return text.substring(0, numLetters).toUpperCase() + text.substring(numLetters, text.length);
    }
  }
}