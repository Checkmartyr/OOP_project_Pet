from pywinauto import Application # pip install pywinauto
import pygetwindow as gw
import pyautogui
import sys
from time import sleep,time
def get_active_window_title():
    active_window = gw.getActiveWindow()
    return active_window.title

sleep(1)
active_window_title = get_active_window_title()

if active_window_title:
    print(f"The currently active window is: {active_window_title}")
else:
    print("No active window found.")



app = Application(backend='uia')
app.connect(title_re=".*Chrome.*")
# try:

#     app.connect(title_re=".*Edge.*")

#     element_name="Address and search bar"

#     dlg = app.top_window()

#     url = dlg.child_window(title=element_name, control_type="Edit").get_value()
# except:

sys.stdout.flush()
short_found = False
founded_time = 0
while 1:
    try:
        active_window_title = get_active_window_title()
        # print("NOW: ",active_window_title)
        # sys.stdout.flush()
        curr_time = time()
        if "Chrome" in active_window_title:
            
            # print(app.is_process_running())
            element_name="ที่อยู่และแถบค้นหา"
            dlg = app.top_window()
            # print(gw.getActiveWindow().title)
            print("opened chrome" if app.is_process_running() else "not open")
            sys.stdout.flush()
            try:
                # print(gw.getActiveWindow()._hWnd)
                try:
                    currtab = dlg.child_window(title="ที่อยู่และแถบค้นหา", control_type="Edit")
                    url = currtab.get_value()
                except:
                    currtab = dlg.child_window(title="Address and search bar", control_type="Edit")
                    url = currtab.get_value()
                # print(currtab)
                
                # print(url)
                # if short_found:
                    
                #     # print(founded_time -curr_time)

                if any( i in url for i in ["/shorts/",'/watch/','/reels/','tiktok']):
                    # print(gw.getActiveWindow()._hWnd)
                    if not short_found:
                        founded_time = time()
                        short_found = True
                    print("[OS]")
                    sys.stdout.flush()
                    if ( curr_time -founded_time ) > 65:
                        print("[BYE]")
                        sys.stdout.flush()
                        pyautogui.hotkey('ctrl', 'w')
                else:
                    print("[NOS]")
                    sys.stdout.flush()
                    short_found = False
                    # print(1)
                # else:
                #     short_found = False


            except EOFError as E:
                # url = dlg.child_window(class_name="OmniboxViewViews", control_type="Edit").get_value()
                print("NO!!! it not BUG!!!!",E)
                sys.stdout.flush()
                # try:
                # # print(gw.getActiveWindow()._hWnd)
                #     currtab = dlg.child_window(title="", control_type="Edit")
                #     url = currtab.get_value()
                #     if any( i in url for i in ["/shorts/",'/watch/','/reels/','tiktok']):
                #         print(gw.getActiveWindow()._hWnd)
                #         print(url)
                #         pyautogui.hotkey('ctrl', 'w')
                #         print(1)
                        
                # except EOFError as E:
                #     print("NO!!! NO!!! it not BUG!!!!")
                    # sys.stdout.flush()
            # print("Am at chrome")
            # sys.stdout.flush()


        elif "Edge" in active_window_title :
            app.connect(title_re=".*Edge.*")
            dlg = app.top_window()
            try:
                # print(gw.getActiveWindow()._hWnd)
                if not short_found:
                        founded_time = time()
                        short_found = True
                print("[OS]")
                sys.stdout.flush()
                if ( curr_time -founded_time ) > 60:
                    print("[BYE]")
                    sys.stdout.flush()
                    pyautogui.hotkey('ctrl', 'w')
                else:
                    print("[NOS]")
                    sys.stdout.flush()
                    short_found = False

            except EOFError as E:
                # url = dlg.child_window(class_name="OmniboxViewViews", control_type="Edit").get_value()
                continue
        else:
            print("[NOS]")
            sys.stdout.flush()
        sleep(0.1)

    except EOFError as e:
        # print(e)
        continue