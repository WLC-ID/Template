import os
import shutil

directory = '{{ cookiecutter.__package.replace('.','/') }}'

srcDir = 'src/main/java'
targetDir = 'src/main/java/' + directory

allfiles = os.listdir(srcDir)
os.makedirs(targetDir, exist_ok=True)
for f in allfiles:
    if '{{ cookiecutter.database }}' == 'no' and 'database' in f:
        shutil.rmtree(os.path.join(srcDir, f))
        continue
    src_path = os.path.join(srcDir, f)
    dst_path = os.path.join(targetDir, f)
    os.rename(src_path, dst_path)

# os.rename("{{ cookiecutter.display_name }}.java", srcDir + '/{{ cookiecutter.display_name }}.java')