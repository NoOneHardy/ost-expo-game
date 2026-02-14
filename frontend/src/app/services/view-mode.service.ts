import {inject, Injectable, OnDestroy} from '@angular/core'
import {Client, IMessage} from '@stomp/stompjs'
import {Store} from '@ngrx/store'
import {setScoreboardViewMode, setRaceViewMode} from '../store/root.actions'

@Injectable({
  providedIn: 'root',
})
export class ViewModeService implements OnDestroy {
  private client: Client | null = null
  private store = inject(Store)

  connect(): void {
    if (this.client?.active) return

    const client = new Client({
      brokerURL: 'ws://localhost:26200/ws',
      onConnect: () => {
        client.subscribe('/topic/mode', (message: IMessage) => {
            try {
              const payload: { isRaceViewMode: boolean } = JSON.parse(message.body)
              if (payload.isRaceViewMode) this.store.dispatch(setRaceViewMode())
              else this.store.dispatch(setScoreboardViewMode())
            } catch (error) {
              console.error('Failed to parse view mode message:', error)
            }
          }
        )
      },
      onStompError: (frame) => {
        console.error('Broker error:', frame.headers['message'], frame.body);
      },
      onWebSocketError: (ev) => {
        console.error('WebSocket error:', ev);
      },
    })

    this.client = client
    client.activate()
    client?.webSocket?.send(this.buildStompFrame('SEND', {
      destination: '/mode',
      'content-type': 'application/json'
    }, ''))
  }

  buildStompFrame(command: string, headers = {}, body = '') {
    const headerLines = Object.entries(headers)
      .map(([k, v]) => `${k}:${v}`)
      .join('\n');
    return `${command}\n${headerLines}\n\n${body}\u0000`;
  }

  disconnect(): void {
    this.client?.deactivate()
    this.client = null
  }

  ngOnDestroy(): void {
    this.disconnect()
  }
}
