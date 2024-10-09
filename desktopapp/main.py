from kivy.app import App
from kivy.uix.boxlayout import BoxLayout
from kivy.uix.video import Video
from kivy.clock import Clock

class M3U8PlayerApp(App):
    def build(self):
        layout = BoxLayout(orientation='vertical')
        
        m3u8_url = "https://www087.anzeat.pro/streamhls/7bfe1a8ced27da6dd288c40e1e696fa3/ep.1.1709029346.360.m3u8"    
    
        self.video = Video(source=m3u8_url)
        layout.add_widget(self.video)
        
        Clock.schedule_once(self.play_video, 1)
        
        return layout

    def play_video(self, dt):
        self.video.state = 'play'

if __name__ == "__main__":
    M3U8PlayerApp().run()