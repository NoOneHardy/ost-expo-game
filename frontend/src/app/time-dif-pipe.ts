import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'timeDif',
})
export class TimeDifPipe implements PipeTransform {

  transform(value: number | null): string {
    if (!value) return (0).toFixed(2).padStart(5, '0') + 's'
    const minutes = Math.floor(value / 60000)
    const seconds = Math.floor((value % 60000 / 1000) / 0.01) / 100
    if (minutes > 0) return `${minutes}min ${seconds.toFixed(2).padStart(5, '0')}s`
    return `${seconds}s`
  }

}
